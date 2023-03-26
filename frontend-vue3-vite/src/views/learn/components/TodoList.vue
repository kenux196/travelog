<template>
  <main>
    <div v-for="(todo, idx) in data" :key="todo.id">
      <div>
        <div>
          <input type="checkbox" :checked="tood.completed" :disabled="todo.completed" @click="completeTodo(todo.id)" />
        </div>
        <div>
          <input type="date" :min="today" disabled :value="todo.date" />
        </div>
        <input type="text" :value="todo.job" />
        <button type="button" data-bs-toggle="dropdown">할일 관리</button>
        <ul>
          <li v-for="item in menu" :key="item.str">
            <a @click="item.func(todo.id)">{{ item.str }}</a>
          </li>
        </ul>
      </div>
      <div v-show="idx + 1 < data.length" />
    </div>
  </main>
</template>

<script setup>
import { inject } from 'vue';

defineProps({
  data: {
    type: Array,
    default: [],
  },
});

const removeTodo = inject('removeTodo');
const completeTodo = inject('completeTodo');
const today = inject('today');
const menu = [
  {
    str: '할일 삭제',
    func: removeTodo,
  },
  {
    str: '할일 완료',
    func: completeTodo,
  },
];
</script>
