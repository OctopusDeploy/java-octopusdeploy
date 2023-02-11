package com.octopus.sdk.features;

import javax.annotation.Nullable;

abstract public class ListArgs {
    @Nullable()
    public Integer skip;

    @Nullable()
    public Integer take;
}
