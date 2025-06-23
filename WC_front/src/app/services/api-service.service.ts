// import { Injectable } from "@angular/core";
// import { HttpClient } from "@angular/common/http";
// import { Observable } from "rxjs";
// import { Clan, ClanCat, Outsider     } from "../models/model";
// import { observableToBeFn } from "rxjs/internal/testing/TestScheduler";

// @Injectable({
//     providedIn: 'root'
// })
// export class ApiserviceService {
//     readonly API_URL = 'http://localhost:8080/PERSO_WC_2024_07/api';

//     constructor(private httpClient: HttpClient) { }

//     getClans(): Observable<any> {
//         return this.httpClient.get<any[]>(this.API_URL + '/clan/all');
//     }

//     getClanById(clanId: number): Observable<Clan> {
//         const url = `${this.API_URL}/clan/${clanId}`;
//         return this.httpClient.get<Clan>(url);
//     }

//     getClanLeader(clanId: number): Observable<ClanCat> {
//         const url = `${this.API_URL}/clan/${clanId}/leader`;
//         return this.httpClient.get<ClanCat>(url);
//     }

//     getClanDeputy(clanId: number): Observable<ClanCat> {
//         const url = `${this.API_URL}/clan/${clanId}/deputy`;
//         return this.httpClient.get<ClanCat>(url);
//     }

//     getClanMedicineCat(clanId: number): Observable<any> {
//         const url = `${this.API_URL}/clan/${clanId}/medcat`;
//         return this.httpClient.get<any[]>(url);
//     }

//     getClanWarriors(clanId: number): Observable<any> {
//         const url = `${this.API_URL}/clan/${clanId}/warriors`;
//         return this.httpClient.get<any[]>(url);
//     }

//     getClanApprentices(clanId: number): Observable<any> {
//         const url = `${this.API_URL}/clan/${clanId}/apprentices`;
//         return this.httpClient.get<any[]>(url);
//     }

//     getClanKits(clanId: number): Observable<any> {
//         const url = `${this.API_URL}/clan/${clanId}/kits`;
//         return this.httpClient.get<any[]>(url);
//     }

//     getClanElders(clanId: number): Observable<any> {
//         const url = `${this.API_URL}/clan/${clanId}/elders`;
//         return this.httpClient.get<any[]>(url);
//     }


//     getClanCats(): Observable<any> {
//         return this.httpClient.get<any[]>(this.API_URL + '/clancat/all');
//     }

//     getClanCatById(catId: number): Observable<ClanCat> {
//         const url = `${this.API_URL}/clancat/${catId}`;
//         return this.httpClient.get<ClanCat>(url);
//     }

//     getAllLeaders(): Observable<any> {
//         const url = `${this.API_URL}/clancat/get/leaders`;
//         return this.httpClient.get<any[]>(url);
//     }

//     getAllDeputies(): Observable<any> {
//         const url = `${this.API_URL}/clancat/get/deputies`;
//         return this.httpClient.get<any[]>(url);
//     }

//     getAllMedicineCats(): Observable<any> {
//         const url = `${this.API_URL}/clancat/get/medcats`;
//         return this.httpClient.get<any[]>(url);
//     }


//     getOutsiders(): Observable<any> {
//         return this.httpClient.get<any[]>(this.API_URL + '/outsider/all');
//     }

//     getOutsiderById(outsiderId: number): Observable<Outsider> {
//         const url = `${this.API_URL}/outsider/${outsiderId}`;
//         return this.httpClient.get<Outsider>(url);
//     }
// }