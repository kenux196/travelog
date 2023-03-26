<template>
  <div class="text-center">
    <h1 class="font-extrabold text-2xl">나의 할 일</h1>
    <h4>{{ today }}</h4>
  </div>
  <div class="text-center">
    <TodoListContainer />
  </div>
</template>
<script setup>
import { inject } from 'vue';
import TodoListContainer from './components/TodoListContainer.vue';

const today = inject('today');
</script>
