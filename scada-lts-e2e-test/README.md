# Testing API

User should focus on two modules:
- scada-lts-e2e-service
- scada-lts-e2e-test

Each module has 3 submodules: 
- api 
- core
- impl

To create tests, it's needed to edit, create new classes inside impl modules. There's no need to edit api and core submodules.

## Changes in scada-lts-e2e-service module

First step is creating `ServiceObject` in package `org.scadalts.e2e.service.impl.services`, that corresponds to the Scada API we want to test. For example, if we want to test `SomeClassAPI` from Scada, we create an `SomeClassServiceObject` class.

`SomeClassServiceObject` class has to implement `WebServiceObject` and override `close()` method that closes the stream and resources associated with it. Also annotations `@Log4j2` and `@Builder` should be added.

```java
@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class SomeClassServiceObject implements WebServiceObject {
}
```

Each ServiceObject class has two fields:

```java
private final URL baseUrl;
private final Client client;
```

baseUrl corresponds to http://localhost:8080/ScadaBR

Then we can write methods that correspond to the methods from our Rest Controller, we want to test. Here's an example:

```java
private E2eResponse<SomeClassResponse> _getValue(SomeClassParams someClassParams) {
   String endpoint = baseUrl + "/api/point_value/getValue/";
   Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
   String xid = someClassParams.getxid();
   logger.info("dataPointXid: {}", xid);
   logger.info("endpoint: {}", endpoint);
   logger.info("cookie: {}", cookie);
   Response response = client.target(endpoint)
           .path(xid)
           .request(MediaType.APPLICATION_JSON_TYPE)
           .cookie(cookie)
           .get();
   return E2eResponseFactory.newResponse(response, SomeClassResponse.class);
}
```

If we have path variables (as in example above), we have to create class, that stores those variables, as seen below:

```java
@Getter
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SomeClassParams implements WebServiceObjectParams {
   private String xid;
}
```

Also, when we know what response will return, we create class for that response, so it can be mapped to that structure:

```java
@Getter
@ToString
@XmlRootElement
@EqualsAndHashCode
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SomeClassResponse {
   private String sth;
}
```

If response returns more things than we expect, annotation `@JsonIgnoreProperties` has to be used.

Next step is creating public method, that other classes can use:

```java
public Optional<E2eResponse<SomeClassResponse>> getValue(SomeClassParams someClassParams, long timeout) {
   try {
       E2eResponse<SomeClassResponse> response = applyWhile(this::_getValue, someClassParams, new StabilityUtil.Timeout(timeout));
       return Optional.ofNullable(response);
   } catch (Throwable e) {
       logger.error(e.getMessage(), e);
       return Optional.empty();
   }
}
```

As seen, the method takes a timeout parameter and uses `applyWhile()` method which is executed until some value is returned as long as timeout is not exceeded.
Instead of `applyWhile()` other methods can be used, which are defined in `ServiceStabilityUtil` class.

Having created our ServiceObject, it has to be added to `ServiceObjectFactory` interface, in this way:

```java
static SomeClassServiceObject newSomeClassServiceObject() {
   return SomeClassServiceObject.builder()
           .client(ClientBuilder.newClient()
                   .register(new JacksonJsonProvider()))
           .baseUrl(E2eConfiguration.baseUrl)
           .build();
}
```

Now our service is created and we can leave `scada-lts-e2e-service` module and go to the
`scada-lts-e2e-test` module.

## Changes in scada-lts-e2e-test module

First step is modifying `TestWithoutPageUtil` class inside `org.scadalts.e2e.test.impl.utils`
 package. We have to add there a method that will create our ServiceObject:

```java
public static E2eResponse<PointValueResponse> getSomeClassValue(SomeClassParams someClassParams, long timeout) {
   try (SomeClassServiceObject someClassWebServiceObject =
                ServiceObjectFactory.newSomeClassServiceObject()) {
       Optional<E2eResponse<SomeClassResponse>> responseOpt = someClassWebServiceObject.getValue(someClassParams,
               timeout);
       return responseOpt.orElseGet(E2eResponse::empty);
   }
}
```

And second method that will provide timeout:

```java
public static E2eResponse<SomeClassResponse> getSomeClassValue(SomeClassParams someClassParams) {
   return getSomeClassValue(someClassParams, TestImplConfiguration.timeout);
}
```

The last step is creating tests.

First thing to do is creating package for our tests inside package `org.scadalts.e2e.test.impl.tests.service`
and classes for them. Then we can create a single test method with `@Test` annotation using given, when, then approach. 

```java
    @Test
    public void test_getEventDetectors_for_data_point_then_status_http_200() {

        //given:
        SomeClassParams someClassParams = new SomeClassParams(someXid);

        //when:
        E2eResponse<SomeClassResponse> getResponse = TestWithoutPageUtil.getSomeClassValue(someClassParams);

        //then:
        assertEquals(200, getResponse.getStatus());
```

If resources are needed like datasources, datapoints we can create them before testing in method with `@Before` annotation.
In the test above, we need to know `someXid` - it can be datapointXid, eventDetectorXid or something else. In order to get it some resources have to be created. 
Example shows how dataSource and dataPoint are created. To initialize resources `Criteria` classes should be used. Each type of resource has its own Criteria class.
Then OjcectCreator has to be created to corresponding resource we want to have and method `createObjects()` has to be called.

```java
    private String dataPointXid;

    @Before
    public void createDataSourceAndPoint() {
        dataPointStartValue = "1223.0";
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(DataPointType.NUMERIC, dataPointStartValue);
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        dataSourcePointObjectsCreator.createObjects();
        dataPointXid = dataPointCriteria.getXid().getValue();
    }
```

The last step, which is also important is deleting created resources. It can be done this way:

```java
@After
    public void clean() {
        dataSourcePointObjectsCreator.deleteObjects();
    }
```

After all these steps, we should have working tests for our API.
