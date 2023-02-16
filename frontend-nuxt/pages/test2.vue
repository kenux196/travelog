<template>
  <div>nuxt test2 page</div>
  <NuxtLink to="/">Go to main</NuxtLink>

  <table>
    <thead>
      <th>#</th>
      <th>제목</th>
      <th>저자</th>
      <th>등록일</th>
    </thead>
    <tbody>
      <tr v-for="book in books" :key="book.id">
        <td>{{ book.id }}</td>
        <td>{{ book.title }}</td>
        <td>{{ book.author }}</td>
        <td>{{ book.createdDate }}</td>
      </tr>
    </tbody>
</table>
</template>

<script setup>
const { data: books } = await useFetch('http://localhost:8080/api/book')
</script>
