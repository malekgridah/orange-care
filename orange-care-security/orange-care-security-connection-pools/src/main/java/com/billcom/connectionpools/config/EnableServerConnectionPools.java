package com.billcom.connectionpools.config;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ServerConnectionPoolsConfigurationMarker.class})
public @interface EnableServerConnectionPools {


}
