package com.clinkast.qcm.services.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class ResetPassword {
    @Pattern.List({
	@Pattern(regexp = "(?=.*\\d).+", message = "{password.digit}"),
	@Pattern(regexp = "(?=.*[a-z]).+", message = "{password.lowercase}"),
	@Pattern(regexp = "(?=.*[A-Z]).+", message = "{password.uppercasecase}"),
	@Pattern(regexp = "(?=\\S+$).+", message = "{password.whitespace}")
    })
    @Length(min=6, max=16)
    @NotNull
    String newPassword;

    @AssertTrue
    boolean reset;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

}
