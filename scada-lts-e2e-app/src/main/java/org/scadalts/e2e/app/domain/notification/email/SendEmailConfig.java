package org.scadalts.e2e.app.domain.notification.email;

import org.scadalts.e2e.common.core.config.E2eConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collections;
import java.util.Locale;
import java.util.Properties;

@Configuration
class SendEmailConfig {

    private static final int TIMEOUT = 60000;

    @Bean
    public JavaMailSender javaMailSender(E2eConfig config) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(config.getHostSmtp());
        mailSender.setPort(config.getPortSmtp());

        mailSender.setUsername(config.getUserSmtp());
        mailSender.setPassword(config.getPasswordSmtp());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", config.isMailSmtpAuthMode());
        props.put("mail.smtp.starttls.enable", config.isMailSmtpStarttlsMode());
        props.put("mail.debug", config.isDebugEmailMode());
        props.put("mail.smtp.connectiontimeout", TIMEOUT);
        props.put("mail.smtp.timeout", TIMEOUT);
        props.put("mail.smtp.writetimeout", TIMEOUT);
        props.put("mail.smtp.socketFactory.port", config.getPortSmtp());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.ssl.trust", config.getHostSmtp());

        return mailSender;
    }

    @Bean("emailMessageTransformator")
    public MessageTransformator emailMessageTransformator(@Qualifier("emailTemplateEngine") TemplateEngine emailTemplateEngine) {
        return new ThymeleafMessageTransformator(emailTemplateEngine, "html/email-notification");
    }

    @Bean("smsMessageTransformator")
    public MessageTransformator smsMessageTransformator(@Qualifier("txtTemplateEngine") TemplateEngine txtTemplateEngine) {
        return new ThymeleafMessageTransformator(txtTemplateEngine, "txt/sms");
    }

    @Bean("emailTemplateEngine")
    public TemplateEngine emailTemplateEngine(@Qualifier("htmlTemplateResolver")ITemplateResolver htmlTemplateResolver,
                                              ResourceBundleMessageSource messageSource) {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver);
        templateEngine.setTemplateEngineMessageSource(messageSource);
        templateEngine.addDialect(new Java8TimeDialect());
        return templateEngine;
    }

    @Bean("txtTemplateEngine")
    public TemplateEngine txtTemplateEngine(@Qualifier("txtTemplateResolver") ITemplateResolver txtTemplateResolver,
                                              ResourceBundleMessageSource messageSource) {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(txtTemplateResolver);
        templateEngine.setTemplateEngineMessageSource(messageSource);
        templateEngine.addDialect(new Java8TimeDialect());
        return templateEngine;
    }


    @Bean("htmlTemplateResolver")
    public ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(0);
        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean("txtTemplateResolver")
    public ITemplateResolver txtTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(0);
        templateResolver.setResolvablePatterns(Collections.singleton("txt/*"));
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("lang");
        return messageSource;
    }

    @Bean
    public CookieLocaleResolver cookieLocaleResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }
}
