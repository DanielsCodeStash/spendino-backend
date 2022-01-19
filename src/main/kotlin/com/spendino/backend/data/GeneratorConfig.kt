package com.spendino.backend.data

import com.spendino.backend.io.YamlPropertyFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties
@PropertySource(value = ["classpath:config.yml"], encoding = "UTF-8", factory = YamlPropertyFactory::class)
class GeneratorConfig {
    lateinit var categories: Map<String, Map<String, List<String>>>
    lateinit var ignored: List<String>
}
