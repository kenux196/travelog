// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  css: ['~/assets/css/pico.min.css'],
  runtimeConfig: {
    apiSecret: '123',
    public: {
      apiBase: '/api',
      adminApiBase: '/admin'
    }
  }
})
