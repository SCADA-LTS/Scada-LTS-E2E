package org.scadalts.e2e.webservice.impl.services.login;

import lombok.*;
import org.scadalts.e2e.webservice.core.WebServiceObjectParams;

import javax.xml.bind.annotation.XmlRootElement;

@Data
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
