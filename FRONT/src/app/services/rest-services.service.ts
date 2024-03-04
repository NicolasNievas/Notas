import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NoteRequest, NoteResponse, TagRequest, TagResponse,} from '../models/note.models';
import { HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root',
})
export class RestServicesService {
  http: any;
  constructor(private httpClient: HttpClient) {}

  private apiUrl = 'http://localhost:8080/api/v1/notes';

  private getFullUrl(endpoint: string): string {
    return `${this.apiUrl}/${endpoint}`;
  }

  public createNoteRequest(note: NoteRequest): Observable<NoteResponse> {
    return this.httpClient.post<NoteResponse>(
      this.getFullUrl('createNote'),
      note
    );
  }

  public getAllNotes(): Observable<NoteResponse[]> {
    return this.httpClient.get<NoteResponse[]>(this.getFullUrl('getAllNotes'));
  }

  public getAllTags(): Observable<TagResponse[]> {
    return this.httpClient.get<TagResponse[]>(this.getFullUrl('getAllTags'));
  }

  public getArchivedNotes(): Observable<NoteResponse[]> {
    return this.httpClient.get<NoteResponse[]>(this.getFullUrl('archived'));
  }

  public getActiveNotes(): Observable<NoteResponse[]> {
    return this.httpClient.get<NoteResponse[]>(this.getFullUrl('active'));
  }

  public getNotesByTagId(tagId: number): Observable<NoteResponse[]> {
    return this.httpClient.get<NoteResponse[]>(
      this.getFullUrl(`getNotesByTagId/${tagId}`)
    );
  }

  public archiveNote(noteId: number): Observable<void> {
    return this.httpClient.put<void>(this.getFullUrl(`archive/${noteId}`), {});
  }

  public unarchiveNote(noteId: number): Observable<void> {
    return this.httpClient.put<void>(
      this.getFullUrl(`unarchive/${noteId}`),
      {}
    );
  }

  public deleteNote(noteId: number): Observable<void> {
    return this.httpClient.delete<void>(
      this.getFullUrl(`deleteNote/${noteId}`)
    );
  }
  public addTagsToNote(noteId: number, tags: TagResponse[]): Observable<any> {
    const tagIds = tags.map((tag) => tag.id_tag);
    const url = `${this.apiUrl}/${noteId}/add_tags`;
    return this.httpClient.post(url, tagIds);
  }

  public removeTagsFromNote(noteId: number, tags: TagResponse[]): Observable<any> {
    const tagIds = tags.map((tag) => tag.id_tag);
    const url = `${this.apiUrl}/${noteId}/remove_tags`;
    return this.httpClient.post(url, tagIds);
  }

  public getNoteById(noteId: number): Observable<NoteResponse> {
    return this.httpClient.get<NoteResponse>(
      this.getFullUrl(`getNoteById/${noteId}`)
    );
  }
  updateNote(noteId: number, updatedData: { content: string }, headers?: HttpHeaders): Observable<any> {
    const url = `${this.apiUrl}/updateNote/${noteId}`;
    return this.httpClient.put(url, updatedData, { headers });
  }
}
