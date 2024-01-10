package org.scadalts.e2e.common.core.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.common.core.types.AuthType;
import org.scadalts.e2e.common.core.types.BrowserRef;
import org.scadalts.e2e.common.core.types.PageLoadStrategy;
import org.scadalts.e2e.common.core.types.TestPlan;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
class E2eConfigDefault implements E2eConfig {

    private int portApp;
    private String cronPattern;
    private boolean continuousMode;
    private String userSmtp;
    private String passwordSmtp;
    private String hostSmtp;
    private int portSmtp;
    private Set<SendTo> sendTo;
    private String sendFrom;
    private boolean debugEmailMode;
    private boolean notificationEmailMode;
    private long deleteEmailFromSentEmailsAfterMs;

    private BrowserRef browserRef;
    private int timeoutMs;
    private File driverFile;
    private boolean headlessMode;
    private boolean driverManagerMode;
    private boolean screenshotMode;
    private boolean fastSetValueMode;
    private boolean proxyMode;
    private PageLoadStrategy pageLoadStrategy;
    private URL reportsUrl;
    private Path reportsFolder;
    private int pollingIntervalMs;
    private int portProxy;
    private String hostProxy;
    private String[] classesTestRefs;
    private String[] pointValuesToTests;
    private String[] browserOptionsArgs;
    private String[] browserOptionsPrefs;
    private int alarmListChangedAfterMs;
    private int alarmListNoChangedAfterMs;
    private int waitingAfterSetPointValueMs;
    private String graphicalViewName;
    private TestPlan[] testPlans;
    private String dataPointToChangeXid;
    private String dataPointToReadXid;

    private Level logLevel;
    private URL urlAppBeingTested;
    private String userName;
    private String password;
    private AuthType authType;

    private String titleEmail;
    private String titleEmailSuccess;
    private String dataSourceName;
    private String dataPointName;

    private String dataPointTargetXid;
    private String dataPointSourceXid;

    private boolean mailSmtpAuthMode;
    private boolean mailSmtpStarttlsMode;

    private boolean checkAuthentication;

    private String refreshSessionCron;

    private boolean unblockSendEmailByCron;
    private String unblockSendSuccessEmailCron;
    private String unblockSendFailEmailCron;
    private boolean loginDisabled;

    @Override
    public String toString() {
        return "\nportApp=" + portApp +
                "\ncronPattern='" + cronPattern + '\'' +
                "\ncontinuousMode=" + continuousMode +
                "\nuserSmtp='" + userSmtp + '\'' +
                "\npasswordSmtp='" + passwordSmtp + '\'' +
                "\nhostSmtp='" + hostSmtp + '\'' +
                "\nportSmtp=" + portSmtp +
                "\nsendTo=" + sendTo +
                "\nsendFrom='" + sendFrom + '\'' +
                "\ndebugEmailMode=" + debugEmailMode +
                "\nnotificationEmailMode=" + notificationEmailMode +
                "\ndeleteEmailFromSentEmailsAfterMs=" + deleteEmailFromSentEmailsAfterMs +
                "\nbrowserRef=" + browserRef +
                "\ntimeoutMs=" + timeoutMs +
                "\ndriverFile=" + driverFile +
                "\nheadlessMode=" + headlessMode +
                "\ndriverManagerMode=" + driverManagerMode +
                "\nscreenshotMode=" + screenshotMode +
                "\nfastSetValueMode=" + fastSetValueMode +
                "\nproxyMode=" + proxyMode +
                "\npageLoadStrategy=" + pageLoadStrategy +
                "\nreportsUrl=" + reportsUrl +
                "\nreportsFolder=" + reportsFolder +
                "\npollingIntervalMs=" + pollingIntervalMs +
                "\nportProxy=" + portProxy +
                "\nhostProxy='" + hostProxy + '\'' +
                "\nclassesTestRefs=" + Arrays.toString(classesTestRefs) +
                "\npointValuesToTests=" + Arrays.toString(pointValuesToTests) +
                "\nbrowserOptionsArgs=" + Arrays.toString(browserOptionsArgs) +
                "\nbrowserOptionsPrefs=" + Arrays.toString(browserOptionsPrefs) +
                "\nalarmListChangedAfterMs=" + alarmListChangedAfterMs +
                "\nalarmListNoChangedAfterMs=" + alarmListNoChangedAfterMs +
                "\nwaitingAfterSetPointValueMs=" + waitingAfterSetPointValueMs +
                "\ngraphicalViewName='" + graphicalViewName + '\'' +
                "\ntestPlans=" + Arrays.toString(testPlans) +
                "\ndataPointToChangeXid='" + dataPointToChangeXid + '\'' +
                "\ndataPointToReadXid='" + dataPointToReadXid + '\'' +
                "\nlogLevel=" + logLevel +
                "\nurlAppBeingTested=" + urlAppBeingTested +
                "\nuserName='" + userName + '\'' +
                "\npassword='" + password + '\'' +
                "\nauthType=" + authType +
                "\ntitleEmail='" + titleEmail + '\'' +
                "\ntitleEmailSuccess='" + titleEmailSuccess + '\'' +
                "\ndataSourceName='" + dataSourceName + '\'' +
                "\ndataPointName='" + dataPointName + '\'' +
                "\ndataPointTargetXid='" + dataPointTargetXid + '\'' +
                "\ndataPointSourceXid='" + dataPointSourceXid + '\'' +
                "\nmailSmtpAuthMode=" + mailSmtpAuthMode +
                "\nmailSmtpStarttlsMode=" + mailSmtpStarttlsMode +
                "\ncheckAuthentication=" + checkAuthentication +
                "\nrefreshSessionCron='" + refreshSessionCron + '\'' +
                "\nunblockSendEmailByCron=" + unblockSendEmailByCron +
                "\nunblockSendSuccessEmailCron='" + unblockSendSuccessEmailCron + '\'' +
                "\nunblockSendFailEmailCron='" + unblockSendFailEmailCron + '\'';
    }
}
