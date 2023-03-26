import { ref } from 'vue';

export const useStorage = () => {
  const KEY = 'my-todo-list';
  const storage_id = ref(0);

  const loadTodos = (initTodos) => {
    let temp_todos = JSON.parse(localStorage.getItem(KEY) || '[]');
    temp_todos.forEach((todo, idx) => {
      todo.id = idx;
    });
    storage_id.value = temp_todos.length;
    initTodos(temp_todos);
  };

  const saveTodos = (todos) => {
    localStorage.setItem(KEY, JSON.stringify(todos.value));
  };

  return {
    storage_id,
    loadTodos,
    saveTodos,
  };
};
