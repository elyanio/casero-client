package caribehostal.caseroclient.datamodel;

import android.support.annotation.NonNull;

import javax.annotation.Nonnull;

/**
 * @author rainermf
 */
public class ClientInfo {
    @Nonnull private final String passport;
    @Nonnull private final String caseroCode;

    public ClientInfo(@NonNull String passport, @NonNull String caseroCode) {
        this.passport = passport;
        this.caseroCode = caseroCode;
    }

    @NonNull
    public String getPassport() {
        return passport;
    }

    @NonNull
    public String getCaseroCode() {
        return caseroCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientInfo that = (ClientInfo) o;

        return passport.equals(that.passport) && caseroCode.equals(that.caseroCode);

    }

    @Override
    public int hashCode() {
        int result = passport.hashCode();
        result = 31 * result + caseroCode.hashCode();
        return result;
    }

    public String component1() {
        return passport;
    }

    public String component2() {
        return caseroCode;
    }
}
