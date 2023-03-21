<template>
  <div class="overflow-hidden text-ellipsis">
    <img :src="bookInfo.thumbnail" class="rounded-lg float-left mr-5 border shadow-md" />
    <p class="font-bold text-lg mb-2">
      {{ bookInfo.title }}
    </p>
    <div class="mb-2 text-sm font-bold">
      {{ bookInfo.authors }} / {{ getPublishDate(bookInfo.datetime) }} / {{ bookInfo.publisher }}
    </div>
    <div class="mb-3">{{ bookInfo.contents }}</div>
  </div>
</template>

<script setup>
const props = defineProps({
  bookInfo: Object,
});
console.log(props.bookInfo);

const getPublishDate = (datetime) => {
  return new Date(datetime).toLocaleDateString();
};
</script>
