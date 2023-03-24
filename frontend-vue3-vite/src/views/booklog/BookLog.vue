<template>
  <div class="flex mx-5">
    <div class="mx-1 mt-10">
      <div class="mx-3 border-b-slate-800" v-for="menu in menus" :key="menu.id">
        <button
          :class="{ 'selected-sub-menu': selectedMenu === menu.id, 'sub-menu': selectedMenu !== menu.id }"
          class="w-40 text-left"
          @click="select(menu.id)"
        >
          {{ menu.name }}
        </button>
      </div>
    </div>
    <RouterView />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import router from '../../router';

const menus = ref([
  {
    id: 0,
    link: '/booklog/bookshelf',
    name: '책장',
    selected: true,
  },
  {
    id: 1,
    link: '/booklog/book-review',
    name: '리뷰',
    selected: false,
  },
  {
    id: 3,
    link: '/booklog/book-highlight',
    name: '하이라이트',
    selected: false,
  },
  {
    id: 4,
    link: '/booklog/book-favorite',
    name: '좋아하는 문장',
    selected: false,
  },
]);

const selectedMenu = ref(0);
const init = () => {
  const menu = menus.value.find((menu) => menu.id === 0);
  router.push(menu.link);
  selectedMenu.value = menu.id;
};
init();

const select = (id) => {
  const menu = menus.value.find((menu) => menu.id === id);
  router.push(menu.link);
  selectedMenu.value = menu.id;
};
</script>

<style scoped>
.sub-menu {
  @apply border-b border-b-slate-400 py-3 hover:text-slate-500;
}
.selected-sub-menu {
  @apply border-b-4 border-b-slate-800 py-3 font-bold;
}
</style>
