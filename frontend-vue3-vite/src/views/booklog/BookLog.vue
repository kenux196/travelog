<template>
  <div class="flex mx-10">
    <div class="mx-1 w-1/5 mt-10">
      <div class="mx-3 my-3 border-b-slate-800">
        <p class="sub-menu">책장</p>
        <p class="sub-menu">북 리뷰</p>
        <p class="sub-menu">하이라이트</p>
        <p class="sub-menu">좋아하는 문장</p>
      </div>
    </div>
    <BookShelf />
  </div>
</template>

<script setup>
import BookShelf from '../../components/book_log/BookShelf.vue';
</script>

<style scoped>
.sub-menu {
  @apply border-b border-b-slate-400 py-3 hover:text-slate-500;
}
</style>
