<template>
  <div>
    <h1>Welcome to the homepage</h1>
    <AppAlert>
      This is an auto-imported component.
    </AppAlert>
    <NuxtLink to="/test">test</NuxtLink> |
    <NuxtLink to="/test2">test2</NuxtLink> |
    <NuxtLink to="/about">about</NuxtLink>
  </div>
</template>
<script setup>
const runtimeConfig = useRuntimeConfig();
</script>
