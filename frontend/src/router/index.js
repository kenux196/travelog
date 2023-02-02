import store from '@/store/store';
import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';

const requireAuth = () => (from, to, next) => {
  if (store.getters.isLogin) {
    console.log('사용자 인증된 상태입니다.');
    return next();
  }
  console.log('미인증 상태입니다. 로그인 페이지로 이동합니다.');
  next('/login');
};

const requireAdmin = () => (from, to, next) => {
  if (store.getters.isAdmin) {
    console.log('관리자 인증된 상태입니다.');
    return next();
  }
  console.log('관리자 미인증 상태입니다. 로그인 페이지로 이동합니다.');
  next('/login');
};

const routes = [
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
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue'),
    beforeEnter: requireAuth(),
  },
  {
    path: '/vue-test',
    name: 'vue test',
    component: () => import('../views/VueTestView.vue'),
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
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
