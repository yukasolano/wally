import { LoaderService } from "./loader.service";

describe('LoaderService', () => {
    let service: LoaderService;
    beforeEach(() => { service = new LoaderService(); });

    it('#show should change loaderState to true',
      (done: DoneFn) => {

      service.loaderState.subscribe(value => {
        expect(value.show).toBe(true);
        done();
      });
      service.show();
    });

    it('#hide should change loaderState to false',
    (done: DoneFn) => {
    service.loaderState.subscribe(value => {
      expect(value.show).toBe(false);
      done();
    });
    service.hide();
  });
});