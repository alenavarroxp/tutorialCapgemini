import { Component, OnInit } from '@angular/core';
import { Loan } from '../model/Loan';
import { MatTableDataSource } from '@angular/material/table';
import { LoanService } from '../loan.service';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { Client } from 'src/app/client/model/Client';
import { Game } from 'src/app/game/model/Game';
import { GameService } from 'src/app/game/game.service';
import { ClientService } from 'src/app/client/client.service';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { LoanEditComponent } from '../loan-edit/loan-edit.component';
import { FilterObj } from '../model/FilterObj';
import { format } from 'date-fns';

@Component({
  selector: 'app-loan-list',
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.scss'],
})
export class LoanListComponent implements OnInit {
  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  clients: Client[];
  games: Game[];
  filterGame: Game;
  filterClient: Client;
  filterDate: Date;
  filterObj: FilterObj = {};

  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = [
    'id',
    'title',
    'name',
    'startDate',
    'endDate',
    'action',
  ];

  constructor(
    private loanService: LoanService,
    public dialog: MatDialog,
    private gameService: GameService,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    this.gameService.getGames().subscribe((games) => (this.games = games));
    this.clientService
      .getClients()
      .subscribe((clients) => (this.clients = clients));
    this.loadPage();
  }

  onCleanFilter() {
    this.filterGame = null;
    this.filterClient = null;
    this.filterDate = null;

    this.onSearch();
  }

  onSearch(): void {
    this.loadPage();
  }

  loadPage(event?: PageEvent) {
    let pageable: Pageable = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [
        {
          property: 'id',
          direction: 'ASC',
        },
      ],
    };

    if (event != null) {
      pageable.pageSize = event.pageSize;
      pageable.pageNumber = event.pageIndex;
    }

    this.filterObj.idGame = this.filterGame?.id ?? null;
    this.filterObj.idClient = this.filterClient?.id ?? null;

    if (this.filterDate == null) {
      this.filterObj.date = null;
    } else {
      const year = this.filterDate.getFullYear().toString();
      const month = (this.filterDate.getMonth() + 1)
        .toString()
        .padStart(2, '0'); // Se agrega 1 porque enero es 0
      const day = this.filterDate.getDate().toString().padStart(2, '0');

      const formatDate = `${year}-${month}-${day}`;
      this.filterObj.date = formatDate ?? null;
    }

    console.log('FILTROS:', this.filterObj);

    this.loanService.getLoans(pageable, this.filterObj).subscribe((data) => {
      console.log('EL DATA ES:', data);
      this.dataSource.data = data.content;
      this.pageNumber = data.pageable.pageNumber;
      this.pageSize = data.pageable.pageSize;
      this.totalElements = data.totalElements;
    });
  }

  createLoan() {
    const dialogRef = this.dialog.open(LoanEditComponent, {
      data: {},
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.ngOnInit();
    });
  }

  deleteLoan(loan: Loan) {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: {
        title: 'Eliminar préstamo',
        description:
          'Atención si borra el préstamo se perderán sus datos.<br> ¿Desea eliminar el préstamo?',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loanService.deleteLoan(loan.id).subscribe((result) => {
          this.ngOnInit();
        });
      }
    });
  }
}
