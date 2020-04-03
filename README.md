# Scada-LTS-E2E
[![Apache-2.0](https://img.shields.io/badge/license-Apache%202-blue)](https://github.com/SCADA-LTS/Scada-LTS-E2E/blob/master/LICENSE)
[![Latest](https://img.shields.io/github/v/release/SCADA-LTS/Scada-LTS-E2E?color=green)](https://github.com/SCADA-LTS/Scada-LTS-E2E)

Tests E2E for ScadaLTS

# v15.0.0
The project is under development.

# v11.0.0
Documentation in preparation.

# v10.0.0

* Main types of E2E tests:
1. PageObject - related to clicking after application ScadaLTS;
2. ApiObject - related to testing of REST services;

* Project structure:
1. scada-lts-e2e-config - load configuration from properties, maven profiles/resources filtering/-Denv=dev/test;

2. scada-lts-e2e-pages - representation of the web part of the ScadaLTS application in the form of PageObject objects. Selenide has been used, Selenium may be used. There is a dependency on the module scada-lts-e2e-config. Page from ScadaLTS should have here representations in the form of the PageObject class, found in the package org.scadalts.e2e.pages.page.page_name;

3. scada-lts-e2e-api - representation of the web part of the ScadaLTS application in the form of ApiObject objects. There is a dependency on the module scada-lts-e2e-config. API from ScadaLTS should have here representations in the form of the ApiObject class, found in the package org.scadalts.e2e.api.service_name;

4. scada-lts-e2e-tests - module in which we write the right tests, using JUnit and Hamcrest, there is also a relationship to the modules: scada-lts-e2e-config, scada-lts-e2e-pages, scada-lts-e2e-api;


* Approach to writing tests:
1. Page Objects pattern:
- the actual page or service is represented by the java class symbolically PageObject / ApiObject;
- the objects on the page or the service parameters are fields respectively PageObject / ApiObject class;
- actions on the website or service are methods of the PageObject / ApiObject class;
2. We create the PageObjct or ApiObject object needed for testing, unless it exists and has the necessary fields, the PageObject / ApiObject objects wrap everything related to the actual page, service, here we use selenium / selenide;
3. Using the PageObject / ApiObject objects, we write JUnit tests, we cannot use the selenium / selenide classes, if necessary it means that we need to implement something in the PageObject / ApiObject classes or create a tool class in scada-lts-e2e-pages;
4. One test class tests one function: e.g. CreateGraphicalViewTest, DeleteGraphicalViewTest in the package: org.scadalts.e2e.tests.page.graphicalviews;
5. org.scadalts.e2e.tests.page.page_name is package for test module scada-lts-e2e-pages;
6. org.scadalts.e2e.tests.api.service_name is package for test module scada-lts-e2e-api;

* Build on:
1. Windows 7 HP SP1 (64-bit);
2. Java JDK 1.8.0_162 (64-bit);
3. Maven 3.6.1;

* Run on:
1. Windows 7 HP SP1/Linux Ubuntu 19.10 (64-bit)
2. Java JRE 1.8.0_162-b12/OpenJDK RE 1.8.0_232-ea-8u232-b09-0ubuntu1-b09 (64-bit);
3. Chrome 78.0.3904.108/79.0.3945.79-1 (64-bit);

* Instalation: (prefer IntelliJ, Windows 7 HP SP1/Linux Ubuntu 19.10)
1. Download project Scada-LTS-E2E;
2. Run in console: mvn clean install -De2eEnv=dev-local, file name: ${project.artifactId}-${project.version}-${e2eEnv}.jar;
3. Run ScadaLTS; (http://localhost:8080/ScadaBR)
4. Install browser Chrome;
5. Run in console: java -jar scada-lts-e2e-tests-x.x.x-SNAPSHOT-dev-local.jar -De2eAll -De2eHeadless=false


* All options:
1. -De2eUrl= - full url to application
2. -De2eHeadless=true/false - headless option
3. -De2eBrowser=chrome/firefox/opera - choose browser (chrome - full supported, opera - not supported headless, firefox - not supported)
4. -De2eTimeout= - timeout in millisecond 
5. -De2eEnv=dev/test - environment 
6. -De2eUser= - username to account
7. -De2ePassword= - password to account 
8. -De2eCtrl= - key ctrl windows/mac/linux
9. -De2eTests='package.ClassTest1;package.ClassTest2;package.ClassTest3' - list class test to run
10. -De2eAll - run all test (ScadaAllTestsSuite)
11. -De2eApi - run api test (ScadaApiTestsSuite)
12. -De2ePage - run page test (ScadaPageTestsSuite)
13. -De2eCheck - run check test, tests readonly, monitoring (ScadaCheckTestsSuite)
14. -De2eConfig - show current configuration

* Versioning rules: Major.Minor.Patch
1. Major+ if backwards incompatible changes, public class/method removed, public api changed;
2. Minor+ if adding new classes, methods, e.g. with tests;
3. Patch+ if tiny corrections, typos, or a patch that works for something that didn't work before;

- mvn versions:set -DnewVersion=x.x.x-SNAPSHOT
- mvn versions:commit
