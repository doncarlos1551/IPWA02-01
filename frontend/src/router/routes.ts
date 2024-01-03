import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('layouts/HauptLayout.vue'),
    children: [{ path: '', component: () => import('pages/StartSeite.vue') }],
  },
  {
    path: '/emission',
    component: () => import('layouts/HauptLayout.vue'),
    children: [{ path: '', component: () => import('pages/EmissionsdatenSeite.vue') }],
  },
  {
    path: '/nachhaltigkeit',
    component: () => import('layouts/HauptLayout.vue'),
    children: [{ path: '', component: () => import('pages/NachhaltigkeitSeite.vue') }],
  },
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNichtGefundenSeite.vue'),
  },
];

export default routes;
