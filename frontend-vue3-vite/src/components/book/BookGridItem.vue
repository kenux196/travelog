<template>
  <div class="w-32">
    <div class="flex">
      <img :src="book.thumbnail" class="w-full shadow-lg shadow-gray-600" />
      <input
        type="checkbox"
        :value="book"
        @change="$emit('select:book', book, $event.target.checked)"
        class="absolute ml-2 mt-2 w-5 h-5"
      />
    </div>
    <div class="text-left mt-1">
      <div class="font-semibold text-sm">{{ book.title }}</div>
      <div class="text-xs">{{ book.authors }}</div>
      <div class="text-xs">{{ book.publisher }}</div>
      <div class="text-xs">{{ getPublishDate(book.publishedDate) }}</div>
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
