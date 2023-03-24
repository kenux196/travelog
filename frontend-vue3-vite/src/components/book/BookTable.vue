<template>
  <div>
    <table>
      <thead class="bg-gray-900 text-white">
        <th></th>
        <th>책</th>
      </thead>
      <tbody>
        <tr v-for="book in bookList" :key="book.id">
          <td>
            <input
              type="checkbox"
              :value="book"
              v-model="selectedBooks"
              class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
            />
          </td>
          <td>
            <BookListItem :book-info="book" />
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
defineProps({
  bookList: Object,
});
</script>

<style lang="scss" scoped></style>
