<template>
  <header class="bg-slate-800 h-20">
    <nav>
      <div>
        <div v-if="authStore.isAdmin" class="text-3xl font-bold">JAMIRO ADMIN</div>
        <div v-else class="text-3xl font-bold">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M12 6.042A8.967 8.967 0 006 3.75c-1.052 0-2.062.18-3 .512v14.25A8.987 8.987 0 016 18c2.305 0 4.408.867 6 2.292m0-14.25a8.966 8.966 0 016-2.292c1.052 0 2.062.18 3 .512v14.25A8.987 8.987 0 0018 18a8.967 8.967 0 00-6 2.292m0-14.25v14.25"
            />
          </svg>
          JAMIRO
        </div>
      </div>
      <div class="flex mt-5">
        <ul v-for="menu in userMainMenus" :key="menu.id" class="flex mx-3">
          <li>
            <button
              :class="{
                'selected-menu': menu.link === routerLinkStore.currentLink,
                'normal-menu': menu.link !== routerLinkStore.currentLink,
              }"
              @click="moveTo(menu.link)"
            >
              {{ menu.name }}
            </button>
          </li>
        </ul>
        <ul class="flex space-x-3">
          <li>
            <router-link v-if="!authStore.isLoggedIn" to="/login" class="menu">Login</router-link>
          </li>
          <li>
            <router-link v-if="!authStore.isLoggedIn" to="/signup" class="menu">Signup</router-link>
          </li>
        </ul>
        <ul v-if="authStore.isAdmin" class="flex space-x-4">
          <li v-if="authStore.isAdmin">
            <router-link to="/admin" class="menu">Admin-Home</router-link>
          </li>
          <li v-if="authStore.isAdmin">
            <router-link to="/admin/member" class="menu">Members</router-link>
          </li>
        </ul>
        <MyInfoBadge v-if="authStore.isLoggedIn" />
      </div>
    </nav>
  </header>
  <main>
    <RouterView />
  </main>
  <footer class="text-center mt-10">
    <p>Copyright © 2023 kenux All rights reserved.</p>
    <address>Contact webmaster for more information. kenux@gmail.com</address>
  </footer>
</template>

<script setup>
import { ref } from 'vue';
import { RouterLink, RouterView } from 'vue-router';
import MyInfoBadge from './components/MyInfoBadge.vue';
import router from './router';
import { useAuthStore } from './stores/auth';
import { useRouterLinkStore } from './stores/router-link-store';

const authStore = useAuthStore();

const userMainMenus = ref([
  {
    link: '/',
    name: 'Home',
  },
  {
    link: '/booklog',
    name: 'Book Log',
  },
  {
    link: '/books',
    name: 'Books',
  },
  {
    link: '/learn/',
    name: '연습장',
  },
]);

const routerLinkStore = useRouterLinkStore();
const moveTo = (to) => {
  if (routerLinkStore.currentLink !== to) {
    routerLinkStore.currentLink = to;
    router.push(to);
  }
};
</script>

<style scoped>
.normal-menu {
  @apply hover:text-yellow-300 font-extralight;
}
.selected-menu {
  @apply hover:text-yellow-300 font-extralight border-b-2 border-b-slate-200;
}

nav {
  @apply flex justify-between mx-10 text-neutral-200 items-center h-full;
}
</style>
