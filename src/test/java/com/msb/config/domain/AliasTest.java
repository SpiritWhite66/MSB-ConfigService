package com.msb.config.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.msb.config.web.rest.TestUtil;

public class AliasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alias.class);
        Alias alias1 = new Alias();
        alias1.setId(1L);
        Alias alias2 = new Alias();
        alias2.setId(alias1.getId());
        assertThat(alias1).isEqualTo(alias2);
        alias2.setId(2L);
        assertThat(alias1).isNotEqualTo(alias2);
        alias1.setId(null);
        assertThat(alias1).isNotEqualTo(alias2);
    }
}
