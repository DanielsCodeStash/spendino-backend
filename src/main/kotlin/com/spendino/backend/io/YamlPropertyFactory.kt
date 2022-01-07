package com.spendino.backend.io

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


class YamlPropertyFactory: PropertySourceFactory {

    @Throws(IOException::class)
    override fun createPropertySource(name: String?, encodedResource: EncodedResource): PropertySource<*> {

        val factory = YamlPropertiesFactoryBean()
        factory.setResources(encodedResource.resource)
        val properties: Properties? = factory.getObject()

        return PropertiesPropertySource(encodedResource.resource.filename!!, properties!!)

    }
}