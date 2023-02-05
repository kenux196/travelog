console.log('javascript 익혀보기');

const year = 2023;
console.log(typeof year);

let month;
console.log(typeof month);

let kim = {
  firstName: 'John',
  lastName: 'Kim',
  age: 35,
  address: 'Seoul',
};
console.log(kim);
console.log(typeof kim);

let add = function (a, b) {
  return a + b;
};
let sum = add(2, 3);
console.log(sum);

let result = (function () {
  return 10 + 20;
})();
console.log(result);

const hi = () => console.log('Hello');
hi();

let hello = user => console.log('Hello, ' + user);
hello('kenux');

let distance = (a, b) => b - a;
console.log(distance(10, 20));

const now = new Date();
console.log(now);
console.log(typeof now);
console.log(now.toISOString());
console.log(now.toLocaleString());

let book = {
  title: '부의 추월차선',
  author: '엠제이 드마코',
  pages: 500,
  price: 12000,
  info: function () {
    console.log(`"${this.title}"은 "${this.author}"가 지은 책입니다.\n가격은 ${this.price}원 입니다.`);
  },
};
book.info();
book.category = 'Book';
console.log(book.category);

function Book(author, pages, price, title) {
  this.author = author;
  this.pages = pages;
  this.price = price;
  this.title = title;
}

let bookShelf = [];
for (let i = 0; i < 5; i++) {
  let book = new Book('kenux', 200, 1000, 'test book' + i);
  bookShelf.push(book);
}
console.log(bookShelf);

const bigInt = 123123123123123123123123123123123123123n;
console.log(typeof bigInt);
