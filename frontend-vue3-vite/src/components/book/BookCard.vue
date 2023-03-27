<template>
  <div class="card w-40 bg-base-100 shadow-xl">
    <figure><img :src="book.thumbnail" /></figure>
    <div class="card-body -mt-5">
      <h2 class="card-title">{{ book.title }}</h2>
      <p class="text-left text-xs">{{ book.authors }}</p>
      <p class="text-left text-xs">{{ book.publisher }}</p>
      <p class="text-left text-xs">{{ getPublishDate(book.publishedDate) }}</p>
      <div class="card-actions justify-end">
        <button class="btn btn-primary btn-sm">담기</button>
      </div>
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
