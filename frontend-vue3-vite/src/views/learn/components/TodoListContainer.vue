<template>
  <TodoListNew />
  <section class="container">
    <div class="row justify-center m-2">
      <TodoListMain />
    </div>
  </section>
</template>
<script setup>
import { ref, provide, readonly } from 'vue';
import { useStorage } from '../compositions/storage';
import TodoListMain from './TodoListMain.vue';
import TodoListNew from './TodoListNew.vue';

const todos = ref([]);
const { loadTodos, saveTodos, storage_id } = useStorage();

provide('todos', readonly(todos));

const initTodos = (init_todos) => {
  todos.value = init_todos;
};
const addTodo = (job, date) => {
  console.log('called addTodo');
  todos.value.push({
    id: storage_id.value++,
    job: job,
    date: date,
    completed: false,
  });
  saveTodos(todos);
};
const removeTodo = (id) => {
  console.log('called removeTodo');
  todos.value.splice(id, 1);
  todos.value.forEach((todo, idx) => {
    todo.id = idx;
  });
  saveTodos(todos);
};
const completeTodo = (id) => {
  console.log('called completeTodo');
  todos.value.find((todo) => todo.id == id).completed = true;
  saveTodos(todos);
};

provide('addTodo', addTodo);
provide('removeTodo', removeTodo);
provide('completeTodo', completeTodo);

loadTodos(initTodos);
</script>
