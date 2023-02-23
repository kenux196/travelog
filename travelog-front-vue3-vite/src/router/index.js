import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from './../stores/auth';
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';

const requireAuth = () => (from, to, next) => {
  if (useAuthStore.isLoggedIn) {
    console.log('This user logged in.');
    return next();
  }
  console.log('This user not logged in. need login');
  next('/login');
};

const requireAdmin = () => (from, to, next) => {
  if (useAuthStore.isAdmin) {
    console.log('This user role is admin!');
    return next();
  }
  console.log('This user role is not admin!~!');
  next('/login');
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
      beforeEnter: requireAuth(),
    },
    {
      path: '/login',
      component: LoginView,
    },
    {
      path: '/admin',
      name: 'admin main',
      component: () => import('../views/admin/AdminMain.vue'),
      beforeEnter: requireAdmin(),
    },
    {
      path: '/admin/member',
      name: 'admin member management',
      component: () => import('../views/admin/AdminMember.vue'),
      beforeEnter: requireAdmin(),
    },
    {
      path: '/learn',
      name: 'learn main',
      component: () => import('../views/learn/LearnMain.vue'),
    },
    {
      path: '/learn/test01',
      name: 'learn test 01',
      component: () => import('../views/learn/LearnTest01.vue'),
    },
  ],
});

export default {
  router,
};
