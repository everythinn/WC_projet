export interface Clan {
    idClan: number;
    name: string;
    description: string;
    territory_type: string;
    preys: string;
    members: ClanCat[];
}

export interface ClanCat {
    idCat: number;
    age: number;
    gender: string;
    appearance: string;
    prefix: string;
    suffix: string;
    clan: Clan;
    rank: string;
    mentor : ClanCat;
    lives: number;
}

export interface Outsider {
    idCat: number;
    age: number;
    gender: string;
    appearance: string;
    name: string;
    status : string;
}

