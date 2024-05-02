import { ChangeEvent, useState } from 'react';

export function useInput(initialValue: string | number | boolean) {
  const [inputValue, setInputValue] = useState(initialValue);

  const handleChange = (e:ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };
  return [inputValue, handleChange];
  // return [inputValue, handleChange] as const;
}