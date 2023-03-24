<template>
  <LayoutHeader />
  <main>
    <RouterView />
  </main>
  <LayoutFooter />
</template>

<script setup>
import LayoutFooter from './components/layout/LayoutFooter.vue';
import LayoutHeader from './components/layout/LayoutHeader.vue';
</script>
