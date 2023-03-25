<template>
  <todo-list-new></todo-list-new>
  <section class="container">
    <div class="row justify-center m-2">
      <TodoListMain />
    </div>
  </section>
</template>
<script>
import { ref, provide, readonly } from 'vue';
import { useStorage } from '../compositions/storage';
import TodoListMain from './TodoListMain.vue';

export default {
  name: 'TodoListContainer',
  setup() {
    const todos = ref([]);
    const { loadTodos, saveTodos, storage_id } = useStorage();

    provide('todos', readonly(todos));

    const initTodos = (init_todos) => {
      todos.value = init_todos;
    };
    const addTodo = (job, date) => {
      todos.value.push({
        id: storage_id.value++,
        job: job,
        date: date,
        completed: false,
      });
      saveTodos(todos);
    };
    const removeTodo = (id) => {
      todos.value.splice(id, 1);
      todos.value.forEach((todo, idx) => {
        todo.id = idx;
      });
      saveTodos(todos);
    };
    const completeTodo = (id) => {
      todos.value.find((todo) => todo.id == id).completed = true;
      saveTodos(todos);
    };

    provide('addTodo', addTodo);
    provide('removeTodo', removeTodo);
    provide('completeTodo', completeTodo);

    loadTodos(initTodos);
  },
  components: { TodoListMain },
};
</script>
