package com.msb.config.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.msb.config.web.rest.TestUtil;

public class ConfigCommonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigCommon.class);
        ConfigCommon configCommon1 = new ConfigCommon();
        configCommon1.setId(1L);
        ConfigCommon configCommon2 = new ConfigCommon();
        configCommon2.setId(configCommon1.getId());
        assertThat(configCommon1).isEqualTo(configCommon2);
        configCommon2.setId(2L);
        assertThat(configCommon1).isNotEqualTo(configCommon2);
        configCommon1.setId(null);
        assertThat(configCommon1).isNotEqualTo(configCommon2);
    }
}
