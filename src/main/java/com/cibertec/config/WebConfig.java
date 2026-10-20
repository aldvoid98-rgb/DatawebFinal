package com.cibertec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private RoleAccessInterceptor roleAccessInterceptor;

    // Lo único que se puede abrir sin haber iniciado sesión
    private static final String[] RUTAS_PUBLICAS = {
        "/formulario", "/login", "/cerrarSesion",
        "/css/**", "/img/**", "/js/**", "/webjars/**",
        "/favicon.ico", "/error", "/error/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 1. Exigir sesión iniciada en TODAS las rutas (excepto las públicas)
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(RUTAS_PUBLICAS)
                .order(1);

        // 2. Comprobar los permisos del rol en TODAS las rutas
        registry.addInterceptor(roleAccessInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(RUTAS_PUBLICAS)
                .order(2);
    }
}
