<template>
  <div class="about">
    <h1>{{ message }}</h1>
    <br />
  </div>
</template>
<script setup>
import { ref } from 'vue';

const message = ref('위험');
</script>
