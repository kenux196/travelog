<template>
  <main>
    <div v-for="(todo, idx) in data" :key="todo.id">
      <div class="flex">
        <div>
          <input
            type="checkbox"
            :checked="todo.completed"
            :disabled="todo.completed"
            @click="completeTodo(todo.id)"
            class="input"
          />
        </div>
        <div>
          <input type="date" :min="today" disabled :value="todo.date" class="input mx-2" />
        </div>
        <input type="text" :value="todo.job" class="input" />
        <div class="dropdown dropdown-hover dropdown-right">
          <label tabindex="0" class="btn m-1 bg-slate-800 hover:bg-slate-700">할일 관리</label>
          <ul tabindex="0" class="dropdown-content menu p-2 shadow bg-base-100 rounded-box w-32">
            <li v-for="item in menu" :key="item.str">
              <a @click="item.func(todo.id)">{{ item.str }}</a>
            </li>
          </ul>
        </div>
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
