<template>
  <div>
    <button @click="showSubMenu" class="flex items-center">
      Book Log
      <span class="ml-1">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="w-4 h-4"
        >
          <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 8.25l-7.5 7.5-7.5-7.5" />
        </svg>
      </span>
    </button>
    <div v-if="showMenu" class="absolute z-10 w-auto text-sm bg-slate-800 rounded-lg shadow-md md:grid-cols-3">
      <div class="p-4 pb-0 text-gray-900 md:pb-4">
        <ul class="space-y-4">
          <li class="menu">
            <router-link to="/my-bookshelf" class="sub-menu">My bookshelf</router-link>
          </li>
          <li>
            <a href="#" class="sub-menu group"> 통계 </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const showMenu = ref(false);

const showSubMenu = () => {
  showMenu.value = !showMenu.value;
};
</script>
<style scoped>
.sub-menu {
  @apply items-center text-gray-300 hover:text-blue-600;
}
</style>
