package es.elvalledeljedi.swlgcdb.usecases.common;

import es.elvalledeljedi.swlgcdb.common.repository.Repository;
import es.elvalledeljedi.swlgcdb.domain.Interactor.AbstractInteractor;
import es.elvalledeljedi.swlgcdb.domain.entity.BaseEntity;
import es.elvalledeljedi.swlgcdb.executor.InteractorExecutor;
import es.elvalledeljedi.swlgcdb.executor.MainThreadExecutor;

/**
 * Created by jcgarcia on 23/05/2016.
 */
public class AddEntity<T extends BaseEntity> extends AbstractInteractor {
    final static int ERROR_CODE = -1;
    Repository<T> localRepository;
    Repository<T> remoteRepository;
    T item;
    AddUseCaseCallback listener;
    int result = 0;

    public AddEntity(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor,
                     Repository<T> localRepository, Repository<T> remoteRepository) {
        super(interactorExecutor, mainThreadExecutor);
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public void execute(T item, AddUseCaseCallback listener) {
        this.item = item;
        this.listener = listener;
        getInteractorExecutor().run(this);
    }

    @Override public void run() {

        try {

            addToRemoteRepository();
            addToLocalRepository();
            sendResultado();
        } catch (Exception e) {
            executionError();
        }
    }

    private void addToLocalRepository() {

        if (result != ERROR_CODE && isRepositoryAvailable(localRepository)) {
            result = localRepository.add(item);
        }

    }

    private void addToRemoteRepository() {

        if (isRepositoryAvailable(remoteRepository)) {
            result = remoteRepository.add(item);
        }
    }

    private boolean isRepositoryAvailable(Repository repository) {
        return repository != null && repository.getDataSource() != null;
    }

    private void sendResultado() {

        if (isResultOk()) {
            executionSuccessful();
        } else {
            executionError();
        }
    }

    private boolean isResultOk() {
        return result != ERROR_CODE;
    }

    protected void executionSuccessful() {
        if (listener != null) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override public void run() {
                    listener.onAddUseCaseResponse(result);
                }
            });
        }
    }

    protected void executionError() {
        if (listener != null) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override public void run() {
                    listener.onAddUseCaseError();
                }
            });
        }
    }

    public interface AddUseCaseCallback {

        void onAddUseCaseResponse(int result);

        void onAddUseCaseError();
    }
}
