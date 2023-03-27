<template>
  <div class="card card-side w-[50rem] bg-base-100 shadow-xl">
    <figure><img :src="book.thumbnail" /></figure>
    <div class="card-body">
      <p class="card-title text-left text-sm font-semibold">{{ book.title }}</p>
      <p class="text-left text-xs">{{ book.authors }}</p>
      <p class="text-left text-xs">{{ book.publisher }}</p>
      <p class="text-left text-xs">{{ getPublishDate(book.publishedDate) }}</p>

    </div>
    <div class="card-actions">
      <button class="btn btn-primary btn-sm">담기</button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  book: Object,
});
defineEmits(['select:book']);

const getPublishDate = (datetime) => {
  return new Date(datetime).toLocaleDateString();
};
</script>
