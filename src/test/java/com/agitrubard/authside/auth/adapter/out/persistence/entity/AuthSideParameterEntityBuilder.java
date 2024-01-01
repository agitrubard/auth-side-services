package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.common.domain.model.TestDataBuilder;

public class AuthSideParameterEntityBuilder extends TestDataBuilder<AuthSideParameterEntity> {

    public AuthSideParameterEntityBuilder() {
        super(AuthSideParameterEntity.class);
    }

    public AuthSideParameterEntityBuilder withId(Long id) {
        data.setId(id);
        return this;
    }

    public AuthSideParameterEntityBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public AuthSideParameterEntityBuilder withDefinition(String definition) {
        data.setDefinition(definition);
        return this;
    }

}
