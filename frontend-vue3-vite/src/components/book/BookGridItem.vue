<template>
  <div class="w-32">
    <div class="flex">
      <img :src="bookInfo.thumbnail" class="w-full shadow-lg shadow-gray-600" />
      <input type="checkbox" class="absolute ml-2 mt-2 w-5 h-5" />
    </div>
    <div class="text-left mt-1">
      <div class="font-semibold text-sm">{{ bookInfo.title }}</div>
      <div class="text-xs">{{ bookInfo.authors }}</div>
      <div class="text-xs">{{ bookInfo.publisher }}</div>
      <div class="text-xs">{{ getPublishDate(bookInfo.datetime) }}</div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  bookInfo: Object,
});

const getPublishDate = (datetime) => {
  return new Date(datetime).toLocaleDateString();
};
</script>
