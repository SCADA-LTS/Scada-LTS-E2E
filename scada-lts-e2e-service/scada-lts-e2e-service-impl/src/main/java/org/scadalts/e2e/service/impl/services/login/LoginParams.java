package org.scadalts.e2e.service.impl.services.login;

import lombok.*;
import org.scadalts.e2e.service.core.services.WebServiceObjectParams;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LoginParams implements WebServiceObjectParams {

    private String userName;
    private String password;
    private String submit = "Login";
}
