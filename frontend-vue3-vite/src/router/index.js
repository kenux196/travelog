import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from './../stores/auth';
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/Auth/LoginView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      component: LoginView,
    },
    {
      path: '/signup',
      component: () => import('../views/Auth/SignupView.vue'),
    },
    {
      path: '/admin',
      name: 'admin main',
      component: () => import('../views/admin/AdminMain.vue'),
      meta: {
        requiresAuth: true,
        isAdmin: true,
      },
    },
    {
      path: '/admin/member',
      name: 'admin member management',
      component: () => import('../views/admin/AdminMember.vue'),
      meta: {
        requiresAuth: true,
        isAdmin: true,
      },
    },
    {
      path: '/learn',
      name: 'learn main',
      component: () => import('../views/learn/LearnMain.vue'),
      children: [
        {
          path: '/learn/todo-list',
          name: 'example todo-list',
          component: () => import('../views/learn/TodoListView.vue'),
        },
        {
          path: '/learn/vue-2',
          name: 'learn test 02',
          component: () => import('../views/learn/LearnVue02.vue'),
        },
        {
          path: '/learn/vue-3',
          name: 'learn test 03',
          component: () => import('../views/learn/LearnVue03.vue'),
        },
        {
          path: '/learn/tailwindcss',
          component: () => import('@/views/learn/Tailwindcss.vue'),
        },
        {
          path: '/learn/learn-js',
          name: 'learn to js',
          component: () => import('../views/learn/LearnJs.vue'),
        },
        {
          path: '/learn/event',
          name: 'Deagu Event',
          component: () => import('../views/learn/DeaguEvent.vue'),
        },
      ],
    },
    {
      path: '/books',
      name: 'book search',
      component: () => import('@/views/booklog/BookView.vue'),
      meta: {
        requiresAuth: true,
      },
    },
    {
      path: '/booklog',
      name: 'user book log',
      component: () => import('@/views/booklog/BookLogView.vue'),
      meta: {
        requiresAuth: true,
      },
      children: [
        {
          path: '/booklog/bookshelf',
          name: 'bookshelf',
          component: () => import('@/components/booklog/BookShelf.vue'),
        },
        {
          path: '/booklog/book-review',
          name: 'book-review',
          component: () => import('@/components/booklog/BookReviewList.vue'),
        },
        {
          path: '/booklog/book-highlight',
          name: 'book-highlight',
          component: () => import('@/components/booklog/BookHighlightList.vue'),
        },
        {
          path: '/booklog/book-favorite',
          name: 'book-favorite',
          component: () => import('@/components/booklog/BookFavoriteList.vue'),
        },
      ],
    },
  ],
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  const isLoggedIn = authStore.isLoggedIn;
  const isAdmin = authStore.isAdmin;

  if (to.meta.requiresAuth && !isLoggedIn) {
    console.log('redirect to "/login"');
    next('/login');
  } else {
    if (to.meta.isAdmin && !isAdmin) {
      alert('관라지 권한이 있어야 합니다. 로그아웃 후 다시 관리자 계정으로 로그인하세요.');
    } else {
      next();
    }
  }
});

export default router;
