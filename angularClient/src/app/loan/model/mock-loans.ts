import { Loan } from './Loan';

export const LOANS_DATA: Loan[] = [
  {
    id: 1,
    game: {
      id: 1,
      title: 'Juego 1',
      age: 6,
      category: { id: 1, name: 'Categoría 1' },
      author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' },
    },
    client: { id: 1, name: 'Juan' },
    startDate: "01/02/2020",
    endDate: "15/02/2020"
  },
   
];
