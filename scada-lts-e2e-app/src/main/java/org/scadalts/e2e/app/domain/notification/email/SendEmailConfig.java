package org.scadalts.e2e.app.domain.notification.email;

import org.scadalts.e2e.common.config.E2eConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collections;
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

    @Bean
    public MessageTransformator messageTransformator(TemplateEngine emailTemplateEngine) {
        return new ThymeleafMessageTransformator(emailTemplateEngine, "html/email-notification");
    }

    @Bean
    public TemplateEngine emailTemplateEngine(ITemplateResolver htmlTemplateResolver,
                                              ResourceBundleMessageSource emailMessageSource) {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver);
        templateEngine.setTemplateEngineMessageSource(emailMessageSource);
        return templateEngine;
    }

    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("lang");
        return messageSource;
    }

    @Bean
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
}
