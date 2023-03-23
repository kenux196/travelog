<template>
  <div class="text-center">
    <div class="font-bold text-3xl my-4">책 검색(with kakao)</div>
    <div>
      <input type="text" v-model="keyword" class="border border-solid border-gray-500 py-2 w-2/4 indent-2" />
      <button @click="searchBook" class="text-white rouned bg-blue-600 hover:bg-blue-400 p-2 w-1/6 ml-1">검색</button>
    </div>
    <hr class="font-bold my-5 mx-5" />
  </div>
  <div class="text-center">
    <div v-if="hasBooks" class="mx-5">
      <table>
        <thead class="bg-gray-900 text-white">
          <th>
            <input
              type="checkbox"
              class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
              @click="selectAll($event.target.checked)"
              v-model="isAllChecked"
            />
          </th>
          <th>책</th>
        </thead>
        <tbody>
          <tr v-for="book in bookList" :key="book.id">
            <td>
              <input
                type="checkbox"
                :id="'check_' + book.id"
                :value="book.id"
                v-model="book.selected"
                @change="selectItem()"
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
              />
            </td>
            <td>
              <BookItem :book-info="book" />
            </td>
          </tr>
        </tbody>
      </table>
      <div class="my-4">
        <button class="border-b-4 border-gray-400 text-gray-400 text-lg font-bold" v-show="!isEnd" @click="getMore">
          more
        </button>
      </div>
      <div class="my-4">
        <button @click="registerBook" class="bg-blue-600 text-white py-4 px-10 font-bold text-lg">등록</button>
      </div>
    </div>
    <div v-else class="font-bold text-lg">검색 결과가 없습니다.</div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { computed, ref } from 'vue';
import BookItem from '@/components/book/BookItem.vue';

const bookList = ref([]);

const hasBooks = computed(() => {
  console.log('book list = ' + bookList.value.length);
  return bookList.value.length > 0 ? true : false;
});

const keyword = ref('');
const searchBook = () => {
  if (keyword.value.length <= 0) {
    alert('검색할 책 정보를 입력하세요.');
    return;
  }
  id.value = 0;
  bookList.value = [];
  getBookInfoFromKakao();
};

const page = ref(1);
const getMore = () => {
  page.value++;
  getBookInfoFromKakao();
};

const isEnd = ref(false);
const getBookInfoFromKakao = () => {
  axios
    .get('https://dapi.kakao.com/v3/search/book', {
      params: {
        sort: 'accuracy', // latest
        page: page.value,
        size: 10,
        target: 'title',
        query: keyword.value,
      },
      headers: {
        Authorization: 'KakaoAK ' + import.meta.env.VITE_KAKAO_REST_API_KEY,
      },
    })
    .then((response) => {
      console.log(response);
      insertBookData(response.data.documents);
      isEnd.value = response.data.meta.is_end;
      console.log(bookList.value);
    })
    .catch((error) => {
      console.log(error);
    });
};

const id = ref(0);
const insertBookData = (books) => {
  books.forEach((book) => {
    book.id = ++id.value;
    console.log(book.id);
    book.authors = getAuthors(book.authors);
    book.selected = false;
    book.isbn = getIsbn(book.isbn);
    bookList.value.push(book);
  });
  console.log(bookList.value);
};

const getAuthors = (authorList) => {
  let authors = '';
  let isFirst = true;
  for (const index in authorList) {
    if (isFirst) {
      authors += authorList[index];
      isFirst = false;
    } else {
      authors += ', ' + authorList[index];
    }
  }
  return authors;
};

const getIsbn = (isbn) => {
  const isbns = isbn.split(' ');
  if (isbns.length < 2) {
    return isbns[0];
  }
  return isbns[1];
};

const isAllChecked = ref(false);
const registerBook = () => {
  // 책 등록 api 호출.
  const bookInfos = bookList.value.filter((book) => book.selected);
  const jsonData = JSON.stringify(bookInfos);
  console.log(jsonData);
  console.log(bookInfos);
  axios
    .post('/api/books', { bookInfos })
    .then(() => {
      console.log('책등록 성공');
      isAllChecked.value = false;
      bookList.value.forEach((book) => {
        book.selected = false;
      });
      bookList.value = [];
      keyword.value = '';
      alert('선택한 책을 저장하였습니다.');
    })
    .catch((e) => {
      console.log(e.message);
    });
};

const selectAll = (checked) => {
  isAllChecked.value = checked;
  bookList.value.forEach((book) => {
    book.selected = isAllChecked.value;
  });
};

const selectItem = () => {
  const unSelectedCount = bookList.value.filter((book) => !book.selected).length;
  if (unSelectedCount > 0) {
    isAllChecked.value = false;
  } else if (unSelectedCount == 0) {
    isAllChecked.value = true;
  }
};
</script>
<style scoped>
th {
  @apply py-3 text-center;
}
td {
  @apply px-2 py-2 text-left border-b;
}
</style>
