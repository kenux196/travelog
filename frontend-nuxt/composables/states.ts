export const useCounter = () => useState<number>('counter', () => 100)
export const useColor = () => useState<string>('color', () => 'pink')
export const useName = () => useState<string>('userName', () => 'kenux')
