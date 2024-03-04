export class NoteRequest{
    content: string = '';
    tags? : TagRequest[] = [];
}

export interface NoteResponse {
    id: number;
    content: string;
    active: boolean ;
    tags: TagResponse[];
  }

export class TagRequest{
    name: string = '';
}
export interface TagResponse{
    id_tag: number ;
    name: string ;
}