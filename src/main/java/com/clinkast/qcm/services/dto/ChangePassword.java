package com.clinkast.qcm.services.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class ChangePassword {

    @Pattern.List({
	@Pattern(regexp = "(?=.*\\d).+", message = "{password.digit}"),
	@Pattern(regexp = "(?=.*[a-z]).+", message = "{password.lowercase}"),
	@Pattern(regexp = "(?=.*[A-Z]).+", message = "{password.uppercasecase}"),
	@Pattern(regexp = "(?=\\S+$).+", message = "{password.whitespace}")
    })
    @Length(min=6, max=16)
    @NotNull
    String oldPassword;
    
    @Pattern.List({
	@Pattern(regexp = "(?=.*\\d).+", message = "{password.digit}"),
	@Pattern(regexp = "(?=.*[a-z]).+", message = "{password.lowercase}"),
	@Pattern(regexp = "(?=.*[A-Z]).+", message = "{password.uppercasecase}"),
	@Pattern(regexp = "(?=\\S+$).+", message = "{password.whitespace}")
    })
    @Length(min=6, max=16)
    @NotNull
    String newPassword;
    

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
   
//    @AssertTrue(message="{password.match}")
//    private boolean isValid() {
//      return this.pass.equals(this.passVerify);
//    }
}
