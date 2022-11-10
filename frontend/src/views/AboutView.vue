<template>
  <div class="about">
    <h1>This is an about page ^^ kenux</h1>
    <Login msg="여기는 kenux의 개인 프로젝트 화면입니다." />
  </div>
</template>
<script>
import Login from '@/components/Login.vue';

export default {
  name: 'AboutView',
  components: {
    Login,
  },
};
</script>
