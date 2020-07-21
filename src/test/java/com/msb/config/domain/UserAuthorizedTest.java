package com.msb.config.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.msb.config.web.rest.TestUtil;

public class UserAuthorizedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAuthorized.class);
        UserAuthorized userAuthorized1 = new UserAuthorized();
        userAuthorized1.setId(1L);
        UserAuthorized userAuthorized2 = new UserAuthorized();
        userAuthorized2.setId(userAuthorized1.getId());
        assertThat(userAuthorized1).isEqualTo(userAuthorized2);
        userAuthorized2.setId(2L);
        assertThat(userAuthorized1).isNotEqualTo(userAuthorized2);
        userAuthorized1.setId(null);
        assertThat(userAuthorized1).isNotEqualTo(userAuthorized2);
    }
}
